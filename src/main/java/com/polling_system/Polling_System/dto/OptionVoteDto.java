package com.polling_system.Polling_System.dto;

public record OptionVoteDto(
        String voteOption,
        Long voteCount
) {
}
