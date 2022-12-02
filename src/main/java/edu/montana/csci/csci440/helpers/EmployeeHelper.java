package edu.montana.csci.csci440.helpers;

import edu.montana.csci.csci440.model.Employee;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EmployeeHelper {

    public static String makeEmployeeTree() {
        // TODO, change this to use a single query operation to get all employees
        Employee employee = Employee.find(1); // root employee
        List<Employee> employees = Employee.all();
        // and use this data structure to maintain reference information needed to build the tree structure
        Map<Long, List<Employee>> employeeMap = new HashMap<>();
        employeeMap.put(employee.getEmployeeId(), new LinkedList<>());
        employees.remove(employee);
        for (Employee emp: employees){
            if (employeeMap.containsKey(emp.getReportsTo())) {
                employeeMap.get(emp.getReportsTo()).add(emp);
            } else {
                employeeMap.put(emp.getReportsTo(), new LinkedList<>());
                employeeMap.get(emp.getReportsTo()).add(emp);
            }
        }
        return "<ul>" + makeTree(employee, employeeMap) + "</ul>";
    }

    // TODO - currently this method just uses the employee.getReports() function, which
    //  issues a query.  Change that to use the employeeMap variable instead
    public static String makeTree(Employee employee, Map<Long, List<Employee>> employeeMap) {
        String list = "<li><a href='/employees" + employee.getEmployeeId() + "'>"
                + employee.getEmail() + "</a><ul>";
        if (employeeMap.containsKey(employee.getEmployeeId())) {
            List<Employee> reports = employeeMap.get(employee.getEmployeeId());
            for (Employee report : reports) {
                list += makeTree(report, employeeMap);
            }
        }
        return list + "</ul></li>";
    }
}
