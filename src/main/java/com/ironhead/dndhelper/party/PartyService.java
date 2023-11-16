package com.ironhead.dndhelper.party;

import java.util.List;

public interface PartyService {
  PartyDto saveParty(PartyDto party);
  // Check is it possible to change value of partyId
  boolean deleteParty(final Long partyId);
  List<PartyDto> getAllParties();
  PartyDto getPartyById(final Long partyId);
}
