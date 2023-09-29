package com.company.CompanyApp.app.enums;

import com.company.CompanyApp.app.annotations.CorrespondingEntity;

import java.util.HashMap;

/**
 * Types of workers the company has
 */
public enum WorkerType {
    @CorrespondingEntity(entityClass = "DataAnalyst")
    DATA_ANALYST("positionType, JUNIOR, ROLE_D, SENIOR, ROLE_C"),


    @CorrespondingEntity(entityClass = "SoftwareDeveloper")
    SOFTWARE_DEVELOPER("positionType, JUNIOR, ROLE_D, SENIOR, ROLE_C"),


    @CorrespondingEntity(entityClass = "Intern")
    INTERN("ROLE_D"),


    @CorrespondingEntity(entityClass = "Manager")
    MANAGER("ROLE_B"),


    @CorrespondingEntity(entityClass = "Admin")
    ADMIN("ROLE_A");


    private final String args;
    private String posTypeFieldName;
    private HashMap<String, String> roles;


    /**
     *
     */
    WorkerType(String args) {
        this.args = args;
    }


    /**
     * Get a key-value pair for a position type and a corresponding role.
     * If the role of a worker type is not position type based, then the
     * method will return a single key-value pair.
     */
    public HashMap<String, String> getRoles() {
        if (roles == null) {
            roles = new HashMap<>();
            String[] data = args.split(",");

            if (data.length == 1) roles.put("", data[0].trim());
            else {
                posTypeFieldName = data[0].trim();

                for (int i = 1; i < data.length; i += 2) {
                    roles.put(data[i].trim(), data[i + 1].trim());
                }
            }
        }

        return roles;
    }


    public String getPosTypeFieldName() {
        return posTypeFieldName;
    }
}