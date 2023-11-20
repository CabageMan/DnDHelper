package com.ironhead.dndhelper.party;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parties")
public class PartyController {

  @Resource(name = "partyService")
  private PartyService partyService;

  /**
   * <p>Get all parties data. The pagination should be used here.</p>
   * @return List<PartyDto>
   */
  @GetMapping
  public List<PartyDto> getParties() {
    return partyService.getAllParties();
  }

  /**
   * <p>Method to get the party data based on the ID.</p>
   * @param id: Long
   * @return PartyDto
   */
  @GetMapping("/party/{id}")
  public PartyDto getParty(@PathVariable Long id) {
    return partyService.getPartyById(id);
  }

  /**
   * <p>Post request to create party.</p>
   * @param partyDto: PartyDto
   * @return PartyDto
   */
  @PostMapping("/party")
  public PartyDto saveParty(final @RequestBody PartyDto partyDto) {
    return partyService.saveParty(partyDto);
  }

  /**
   * <p>Delete party from based on the id.</p>
   * @param id: Long
   * @return Boolean
   */
  @DeleteMapping("/party/{id}")
  public Boolean deleteParty(@PathVariable Long id) {
    return partyService.deleteParty(id);
  }
}
