package com.company.CompanyApp.app.enums;

import com.company.CompanyApp.app.annotations.CorrespondingEntity;
import com.company.CompanyApp.app.annotations.Role;

/**
 * Types of workers the company has
 */
public enum WorkerType {
    @CorrespondingEntity(entityClass = "DataAnalyst")
    @Role(isPositionTypeBased = true, roles = {"JUNIOR",
                                       "ROLE_D",
                                       "SENIOR",
                                       "ROLE_C",
                                       "ROLE_D"})
    DATA_ANALYST,


    @CorrespondingEntity(entityClass = "SoftwareDeveloper")
    @Role(isPositionTypeBased = true, roles = {"JUNIOR",
                                       "ROLE_D",
                                       "SENIOR",
                                       "ROLE_C",
                                       "ROLE_D"})
    SOFTWARE_DEVELOPER,


    @CorrespondingEntity(entityClass = "Intern")
    @Role(roles = "ROLE_D")
    INTERN,


    @CorrespondingEntity(entityClass = "Manager")
    @Role(roles = {"ROLE_B",
                   "ROLE_C",
                   "ROLE_D"})
    MANAGER,


    @CorrespondingEntity(entityClass = "Admin")
    @Role(roles = {"ROLE_A",
                   "ROLE_B",
                   "ROLE_C",
                   "ROLE_D"})
    ADMIN
}