package com.polling_system.Polling_System.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OptionVote {
    //@JsonProperty("VoteOption")
     private String voteOption;
   // @JsonProperty("VoteCount")
     private  Long voteCount =0L;
}
