package com.example.demo;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/agreements")
public class TestContoller {
    @GetMapping()
    public List<Agreements> getAllContracts() throws SQLException {

        JdbcTemplate jdbcTemplate = createDataSource();

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GETAGREEMENTS")
                .withSchemaName("AGREEMENTS").declareParameters(new SqlOutParameter("C1", Types.REF_CURSOR));

        Map<String, Object> result = call.execute();
        List<Agreements> empList = (List<Agreements>) result.get("C1");
        System.out.println(result.toString());
        System.out.println("Done!");

        return empList;

    }

    @PostMapping("/create")
    public void creatAgreement(@RequestBody Agreements agreement) throws SQLException {

        JdbcTemplate jdbcTemplate = createDataSource();

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("V_CONTRACT_ID", agreement.getContractId());
        params.put("V_TITLE", agreement.getTitle());
        params.put("V_DESCRIPTION", agreement.getContractDesc());

        SqlParameterSource in = new MapSqlParameterSource().addValues(params);

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName("CREATEAGREEMENTS")
                .withSchemaName("AGREEMENTS");

        Map<String, Object> result = call.execute(in);
    }

    private JdbcTemplate createDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        String connectionUrl = "jdbc:oracle:thin:@localhost:1521/xe";

        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl(connectionUrl);
        ds.setUsername("system");
        ds.setPassword("oracle");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate;
    }
}