package com.pluralsight.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ride> getRides() {
		/*
		Ride ride = new Ride();
		ride.setName("Corner Canyon");
		ride.setDuration(120);
		List <Ride> rides = new ArrayList<>();
		rides.add(ride);
		*/
		
		List<Ride> rides = jdbcTemplate.query("Select * from ride", new RideRowMapper());
		
		return rides;
	}

	
	@Override
	public Ride createRide(Ride ride) {
		// TODO Auto-generated method stub
		

		
		
		//Method 1
		//jdbcTemplate.update("insert into ride(name, duration) values(?,?)", ride.getName(), ride.getDuration());
		
		//Method 2
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		List<String> columns = new ArrayList<>();
		columns.add("name");
		columns.add("duration");
		
		insert.setTableName("ride");
		insert.setColumnNames(columns);
		
		Map<String, Object> data = new HashMap<>();
		data.put("name", ride.getName());
		data.put("duration",  ride.getDuration());
		
		insert.setGeneratedKeyName("id");
		
		Number key = insert.executeAndReturnKey(data);
		System.out.println(key);
		return getRide(key.intValue());
		
		//Method 3
		/*
		KeyHolder keyholder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement("insert into ride(name, duration) values(?,?)", new String [] {"id"});
				ps.setString(1,  ride.getName());
				ps.setInt(2,  ride.getDuration());
				return ps;
			}
		}, keyholder);
		
		Number id = keyholder.getKey();
		return getRide(id.intValue());
		*/
		
	//	return null;
	}
	
	@Override
	public Ride getRide(Integer id){
		Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);
		return ride;
		
	}


	@Override
	public Ride updateRide(Ride ride) {
		jdbcTemplate.update("update ride set name = ?, duration = ? where id = ?", ride.getName(), ride.getDuration(), ride.getId());
		return ride;
	}


	@Override
	public void updateRide(List<Object[]> pairs) {
		jdbcTemplate.batchUpdate("update ride set ride_date = ? where id = ?", pairs);
		
	}
	
	@Override
	public void deleteRide(Integer id) {
		jdbcTemplate.update("delete from ride where id = ?", id);
	}
	
	
	
}
