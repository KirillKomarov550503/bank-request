package com.netcracker.komarov.request.dao.repository;

import com.netcracker.komarov.request.dao.entity.Request;
import com.netcracker.komarov.request.dao.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Modifying
    void deleteRequestById(long id);

    Request findRequestByEntityIdAndAndStatus(long entityId, Status status);

//    @Modifying
//    @Query("delete from Request r where r.entityId= :entity_id and r.status= :status")
//    void deleteRequestByEntityIdAndStatus(@Param("entity_id") long entityId, @Param("status") Status status);

    @Modifying
    void deleteRequestByEntityIdAndStatus(long entityId, Status status);
}
