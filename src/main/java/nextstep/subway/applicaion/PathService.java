package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.PathResponse;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.Path;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.SubwayMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathService {
    private LineService lineService;
    private StationService stationService;

    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    public PathResponse findPath(Long source, Long target, boolean minimumTime) {
        Station upStation = stationService.findById(source);
        Station downStation = stationService.findById(target);
        List<Line> lines = lineService.findLines();
        SubwayMap.SectionPathType sectionPathType = minimumTime ? SubwayMap.SectionPathType.DURATION : SubwayMap.SectionPathType.DISTANCE;
        SubwayMap subwayMap = new SubwayMap(lines, sectionPathType);
        Path path = subwayMap.findPath(upStation, downStation);
        return PathResponse.of(path);
    }
}
