package com.company.CompanyApp.app.dto;

import com.company.CompanyApp.app.entity.worker.Department;
import com.company.CompanyApp.validation.annotations.Numeric;
import com.company.CompanyApp.validation.dto.ModelData;
import com.company.CompanyApp.validation.dto.ModelDataContainer;

import java.util.List;

/**
 * An extension of the default ModelDataContainer dto.
 * The ModelDataContainer is needed, because the data of the
 * department can be altered (or created), but in addition this container allows
 * to show the corresponding workers as records or search for other departments.
 */
public class DepartmentContainer extends ModelDataContainer {
    @Numeric
    String departmentId;    //Requested department's id
    Department department;


    //CONSTRUCTORS
    public DepartmentContainer(Department department) {
        this.department = department;
    }


    public DepartmentContainer(List<ModelData> modelData) {
        super(modelData);
    }


    public DepartmentContainer() {}


    //Setters & Getters
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}