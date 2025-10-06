package com.polling_system.Polling_System.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    //@JsonProperty("VoteCount")
    private Long pollid=0L;
    //@JsonProperty("OptionVoteIndex")
    private int OptionVoteIndex;
}
