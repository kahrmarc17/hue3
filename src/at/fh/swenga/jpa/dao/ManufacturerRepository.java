package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.ManufacturerModel;
import at.fh.swenga.jpa.model.InstrumentModel;

@Repository

public interface ManufacturerRepository extends JpaRepository<ManufacturerModel, Integer> {

	@Transactional
	ManufacturerModel findFirstByName(String manufacturerName);

}
