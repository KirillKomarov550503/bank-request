package com.netcracker.komarov.request.dao.repository;

import com.netcracker.komarov.request.dao.entity.Request;
import com.netcracker.komarov.request.dao.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Modifying
    void deleteRequestById(long id);

    Request findRequestByEntityIdAndAndStatus(long entityId, Status status);
}
