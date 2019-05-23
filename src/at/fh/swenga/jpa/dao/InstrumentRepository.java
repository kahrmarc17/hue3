package at.fh.swenga.jpa.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.model.InstrumentModel;

@Repository
@Transactional
public interface InstrumentRepository extends JpaRepository<InstrumentModel, Integer> {

	//query 2 - find the instruments by category
	List<InstrumentModel> findByCategory(String category);
	
	//query 3 - find the instruments by category or name
	List<InstrumentModel> findByCategoryOrName(String category, String name);

	//query 4 - find the instruments by their names --> Query Annotation
	@Query("select i from InstrumentModel i where LOWER(i.category) LIKE CONCAT('%',LOWER(:name), '%') or i.name = :name")
	List<InstrumentModel> findByAnyName(@Param("name") String name);

	//query 5 - find the instruments by their names --> Named Query Annotation
	List<InstrumentModel> findByNamedQuery(@Param("search")String searchString);
	
	//query 6 - find the top 15 instruments ordered by name
	public List<InstrumentModel> findTop15ByOrderByName();
	
	//query 7 - count all instruments of a special category
	int countByCategory(String category);

	//query 8 - delete instruments by their names
	List<InstrumentModel> deleteByName(String name);
	
	//query 9 - delete all instruments of a special manufacturer
	int deleteByManufacturerName(String name);
	
	//query 10 - find all instruments of a special manufacturer and sort them by their names
	@Query("SELECT i "
			+ "FROM InstrumentModel AS i "
			+ "JOIN i.manufacturer AS m "
			+ "WHERE m.name = :name "
			+ "OR i.name = :name "
			+ "ORDER BY i.name ASC")
	
	public List<InstrumentModel> findByManufacturerNameOrderByNameAsc(@Param("name") String searchString);

	//query 11 - find all instruments where category or name contains a given searchstring
	List<InstrumentModel> findByCategoryContainingOrNameContainingAllIgnoreCase(String category, String name);

}
