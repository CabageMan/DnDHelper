package com.ironhead.dndhelper.party;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("partyService")
public class DefaultPartyService implements PartyService {

  @Autowired
  private PartyRepository partyRepository;

  /**
   * Create a Party based on the data sent to the service class.
   *
   * @param party PartyDto
   * @return DTO representation of the customer
   */
  @Override
  public PartyDto saveParty(PartyDto party) {
    Party partyEntity = populatePartyEntity(party);
    return populatePartyData(partyRepository.save(partyEntity));
  }

  /**
   * Delete party based on the party ID.
   * Also, it's possible to delete party based on the entity
   * (passing JPA entity class as method parameter. But it also returns void, and we need return boolean. Check this moment)
   * @param partyId Long
   * @return boolean
   */
  @Override
  public boolean deleteParty(final Long partyId) {
    // Strange solution. May be to check for existing before deleting.
    partyRepository.deleteById(partyId);
    return true;
  }

  /**
   * Method to return the list of all the parties in the system.
   * Improve this method to use pagination (it's important).
   * @return List<PartyDto>
   */
  @Override
  public List<PartyDto> getAllParties() {
    List<PartyDto> partyDtoList = new ArrayList<>();
    List<Party> partiesList = partyRepository.findAll();
    partiesList.forEach(party -> { partyDtoList.add(populatePartyData(party)); });
    return partyDtoList;
  }

  /**
   * Get party by ID.
   * @param partyId: Long
   * @return PartyDto
   */
  @Override
  public PartyDto getPartyById(final Long partyId) {
    return populatePartyData(
            partyRepository
                    .findById(partyId)
                    .orElseThrow(() -> new EntityNotFoundException("Party not found"))
    );
  }

  /**
   * Internal method to map the front end party object to the JPA party entity.
   *
   * @param partyDto: PartyDto
   * @return Party
   */
  private Party populatePartyEntity(PartyDto partyDto) {
    Party party = new Party();
    // Use constructor instead setter
    party.setName(partyDto.getName());
    party.setCreatedTime(LocalDateTime.now());
    party.setUpdatedTime(LocalDateTime.now());
    return party;
  }

  /**
   * Internal method to convert Party JPA entity to the Data Transfer Object.
   *
   * @param party: Party
   * @return PartyDto
   */
  private PartyDto populatePartyData(final Party party) {
    PartyDto partyDto = new PartyDto();
    partyDto.setId(party.getId());
    partyDto.setName(party.getName());
    return partyDto;
  }
}
