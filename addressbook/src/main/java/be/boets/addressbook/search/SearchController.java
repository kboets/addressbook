package be.boets.addressbook.search;

import be.boets.addressbook.dto.PersonDto;
import be.boets.addressbook.dto.SearchCriteria;
import be.boets.addressbook.mapper.PersonMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public List<PersonDto> search(SearchCriteria searchCriteria) {
        return personMapper.toDtos(searchService.search(searchCriteria));
    }
}
