package com.ironhead.dndhelper.controllers;

import com.ironhead.dndhelper.dto.PartyData;
import com.ironhead.dndhelper.models.Party;
import com.ironhead.dndhelper.services.PartyService;
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
   * @return List<PartyData>
   */
  @GetMapping
  public List<PartyData> getParties() {
    return partyService.getAllParties();
  }

  /**
   * <p>Method to get the party data based on the ID.</p>
   * @param id
   * @return PartyData
   */
  @GetMapping("/party/{id}")
  public PartyData getParty(@PathVariable Long id) {
    return partyService.getPartyById(id);
  }

  /**
   * <p>Post request to create party.</p>
   * @param partyData
   * @return PartyData
   */
  @PostMapping("/party")
  public PartyData saveParty(final @RequestBody PartyData partyData) {
    return partyService.saveParty(partyData);
  }

  /**
   * <p>Delete party from based on the id.</p>
   * @param id
   * @return Boolean
   */
  @DeleteMapping("/party/{id}")
  public Boolean deleteParty(@PathVariable Long id) {
    return partyService.deleteParty(id);
  }
}
