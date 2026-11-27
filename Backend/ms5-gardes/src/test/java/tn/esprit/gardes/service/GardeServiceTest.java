package tn.esprit.gardes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.gardes.dto.GardeRequest;
import tn.esprit.gardes.dto.GardeResponse;
import tn.esprit.gardes.entity.Garde;
import tn.esprit.gardes.entity.Zone;
import tn.esprit.gardes.exception.ResourceConflictException;
import tn.esprit.gardes.repository.GardeRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GardeServiceTest {

    @Mock
    private GardeRepository repository;

    @Mock
    private ZoneService zoneService;

    private GardeService service;
    private Zone zone;

    @BeforeEach
    void setUp() {
        service = new GardeService(repository, zoneService);
        zone = Zone.builder().id(1L).nom("Centre-ville").ville("Tunis")
                .latitude(36.8065).longitude(10.1815).build();
    }

    @Test
    void createReturnsTheSavedShift() {
        GardeRequest request = validRequest();
        when(zoneService.findById(1L)).thenReturn(zone);
        when(repository.save(any(Garde.class))).thenAnswer(invocation -> {
            Garde garde = invocation.getArgument(0);
            garde.setId(10L);
            return garde;
        });

        GardeResponse response = service.create(request);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.pharmacieId()).isEqualTo(25L);
        assertThat(response.zone()).isEqualTo("Centre-ville");
    }

    @Test
    void createRejectsAnOverlappingShift() {
        GardeRequest request = validRequest();
        when(repository.existsByPharmacieIdAndDateAndHeureDebutLessThanAndHeureFinGreaterThan(
                request.pharmacieId(), request.date(), request.heureFin(), request.heureDebut()))
                .thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceConflictException.class)
                .hasMessageContaining("deja une garde");
    }

    @Test
    void createRejectsInvalidHours() {
        GardeRequest request = new GardeRequest(25L, 1L, LocalDate.now().plusDays(1),
                LocalTime.of(20, 0), LocalTime.of(8, 0));

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("heureFin");
    }

    private GardeRequest validRequest() {
        return new GardeRequest(25L, 1L, LocalDate.now().plusDays(1),
                LocalTime.of(8, 0), LocalTime.of(20, 0));
    }
}
