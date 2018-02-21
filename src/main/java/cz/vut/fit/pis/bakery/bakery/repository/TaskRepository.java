package cz.vut.fit.pis.bakery.bakery.repository;

import cz.vut.fit.pis.bakery.bakery.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Date> {

    @Query("SELECT p FROM Task p WHERE p.date >= :from AND p.date <= :to")
    List<Task> getTasksInPeriod(@Param("from") Date from, @Param("to") Date to);

    List<Task> findByDate(Date date);
}
