package com.polling_system.Polling_System.repository;

import com.polling_system.Polling_System.models.Polls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollsRepository extends JpaRepository<Polls, Long>
{
}
