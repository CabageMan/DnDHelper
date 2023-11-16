package com.ironhead.dndhelper.services;

import com.ironhead.dndhelper.dto.PartyData;

import java.util.List;

public interface PartyService {
  PartyData saveParty(PartyData party);
  // Check is it possible to change value of partyId
  boolean deleteParty(final Long partyId);
  List<PartyData> getAllParties();
  PartyData getPartyById(final Long partyId);
}
