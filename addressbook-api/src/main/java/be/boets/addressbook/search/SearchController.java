package be.boets.addressbook.search;

import be.boets.addressbook.dto.PersonDto;
import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.mapper.PersonMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/search")
public class SearchController {

    private final SearchService searchService;
    private final PersonMapper personMapper;

    public SearchController(SearchService searchService, PersonMapper personMapper) {
        this.searchService = searchService;
        this.personMapper = personMapper;
    }

    @PostMapping
    public List<PersonDto> search(@RequestBody SearchCriteria searchCriteria) {
        return personMapper.toDtos(searchService.search(searchCriteria));
    }
}
