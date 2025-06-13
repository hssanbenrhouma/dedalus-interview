import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../models/employee.model';

@Component({
  selector: 'app-department',
  standalone: false,
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.scss']
})
export class DepartmentComponent implements OnInit {

  departmentId!: string;
  employees: Employee[] = [];
  showAddEmployeeModal = false;
  newEmployee: Partial<Employee> = {};

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService
  ) { }

  ngOnInit(): void {
    this.departmentId = this.route.snapshot.paramMap.get('id')!;
    if (this.departmentId === 'unassigned') {
      this.loadUnassignedEmployees();
    } else {
      this.loadEmployees();
    }
  }

  loadEmployees(): void {
    this.employeeService.getEmployeesByDepartment(this.departmentId).subscribe(
      (data: Employee[]) => {
        this.employees = data;
      },
      (error) => {
        console.error('Error fetching employees', error);
      }
    );
  }

  loadUnassignedEmployees(): void {
    this.employeeService.getUnassignedEmployees().subscribe(
      (data: Employee[]) => {
        this.employees = data;
      },
      (error) => {
        console.error('Error fetching unassigned employees', error);
      }
    );
  }

  deleteEmployee(employeeId: string): void {
    this.employeeService.deleteEmployee(employeeId).subscribe(() => {
      this.loadEmployees();
    }, (error) => {
      console.error('Error deleting employee', error);
    });
  }
  openAddEmployeeModal() {
    this.showAddEmployeeModal = true;
    this.newEmployee = {};
  }

  closeAddEmployeeModal() {
    this.showAddEmployeeModal = false;
  }

  addEmployee() {
    if (!this.newEmployee.fullName) return;
    const employeeToAdd = {
      fullName: this.newEmployee.fullName as string,
      address: this.newEmployee.address as string,
      phoneNumber: this.newEmployee.phoneNumber as string,
      email: this.newEmployee.email as string,
      departmentId: this.departmentId
    };
    this.employeeService.createEmployee(employeeToAdd).subscribe(() => {
      this.loadEmployees();
      this.closeAddEmployeeModal();
    });
  }
}