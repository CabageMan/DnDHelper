package com.ironhead.dndhelper.services;

import com.ironhead.dndhelper.dto.PartyData;
import com.ironhead.dndhelper.models.Party;
import com.ironhead.dndhelper.repositories.PartyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("partyService")
public class DefaultPartyService implements PartyService {

  @Autowired
  private PartyRepository partyRepository;

  /**
   * Create a Party based on the data sent to the service class.
   *
   * @param party
   * @return DTO representation of the customer
   */
  @Override
  public PartyData saveParty(PartyData party) {
    Party partyEntity = populatePartyEntity(party);
    return populatePartyData(partyRepository.save(partyEntity));
  }

  /**
   * Delete party based on the party ID.
   * Also it's possible to delete party based on the entity
   * (passing JPA entity class as method parameter. But it also returns void, and we need return boolean. Check this moment)
   * @param partyId
   * @return boolean
   */
  @Override
  public boolean deleteParty(final Long partyId) {
    // Strange solution. May be check for existing before deleting.
    partyRepository.deleteById(partyId);
    return true;
  }

  /**
   * Method to return the list of all the parties in the system.
   * Improve this method to use pagination (it's important).
   * @return List<PartyData>
   */
  @Override
  public List<PartyData> getAllParties() {
    List<PartyData> partyDataList = new ArrayList<>();
    List<Party> partiesList = partyRepository.findAll();
    partiesList.forEach(party -> { partyDataList.add(populatePartyData(party)); });
    return partyDataList;
  }

  /**
   * Get party by ID.
   * @param partyId
   * @return PartyData
   */
  @Override
  public PartyData getPartyById(final Long partyId) {
    return populatePartyData(
            partyRepository
                    .findById(partyId)
                    .orElseThrow(() -> new EntityNotFoundException("Party not found"))
    );
  }

  /**
   * Internal method to map the front end party object to the JPA party entity.
   *
   * @param partyData
   * @return Party
   */
  private Party populatePartyEntity(PartyData partyData) {
    Party party = new Party();
    // Use constructor instead setter
    party.setName(partyData.getName());
    return party;
  }

  /**
   * Internal method to convert Party JPA entity to the Data Transfer Object.
   *
   * @param party
   * @return PartyData
   */
  private PartyData populatePartyData(final Party party) {
    PartyData partyData = new PartyData();
    partyData.setId(party.getId());
    partyData.setName(party.getName());
    return partyData;
  }
}
