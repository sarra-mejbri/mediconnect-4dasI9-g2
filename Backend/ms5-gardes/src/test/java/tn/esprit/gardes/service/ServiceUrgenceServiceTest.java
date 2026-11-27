package tn.esprit.gardes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.gardes.dto.ServiceUrgenceResponse;
import tn.esprit.gardes.entity.ServiceUrgence;
import tn.esprit.gardes.entity.Zone;
import tn.esprit.gardes.enums.TypeUrgence;
import tn.esprit.gardes.repository.ServiceUrgenceRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceUrgenceServiceTest {

    @Mock
    private ServiceUrgenceRepository repository;

    @Mock
    private ZoneService zoneService;

    private ServiceUrgenceService service;
    private ServiceUrgence urgence;

    @BeforeEach
    void setUp() {
        service = new ServiceUrgenceService(repository, zoneService);
        Zone zone = Zone.builder().id(1L).nom("Centre-ville").ville("Tunis")
                .latitude(36.8065).longitude(10.1815).build();
        urgence = ServiceUrgence.builder().id(4L).nom("Hopital Charles Nicolle")
                .type(TypeUrgence.HOPITAL).adresse("Boulevard du 9 Avril 1938")
                .latitude(36.8028).longitude(10.1630).telephone("+216 71 578 000")
                .ouvert24h(true).zone(zone).build();
    }

    @Test
    void findNearestIncludesCalculatedDistance() {
        when(repository.findNearest(36.8065, 10.1815, 50)).thenReturn(List.of(urgence));

        List<ServiceUrgenceResponse> result = service.findNearest(36.8065, 10.1815, 50);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).distanceKm()).isPositive().isLessThan(5);
    }

    @Test
    void findNearestRejectsInvalidCoordinates() {
        assertThatThrownBy(() -> service.findNearest(95, 10, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("lat");
    }
}
