import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../../services/department.service';
import { EmployeeService } from '../../services/employee.service';
import { Department } from '../../models/department.model';
import { Employee } from '../../models/employee.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  searchTerm: string = '';
  searchResults: Employee[] = [];
  searching: boolean = false;
  departments: Department[] = [];
  unassignedEmployeesCount: number = 0;
  newDepartmentName: string = '';
  showAddDepartmentModal: boolean = false;

  constructor(
    private departmentService: DepartmentService,
    private employeeService: EmployeeService,
    private router: Router) { }

  ngOnInit(): void {
    this.loadDepartments();
    this.loadUnassignedEmployeesCount();
  }


  loadDepartments(): void {
    this.departmentService.getDepartments().subscribe(departments => {
      this.departments = departments;
    });
  }

  loadUnassignedEmployeesCount(): void {
    this.employeeService.getUnassignedEmployees().subscribe(unassignedEmployees => {
      this.unassignedEmployeesCount = unassignedEmployees.length;
    });
  }

  goToDepartmentByEmployee(employee: Employee) {
    if (employee.department && employee.department.id) {
      this.router.navigate(['/departments', employee.department.id]);
    } else {
      this.router.navigate(['/departments', 'unassigned']);
    }
  }
  navigateToUnassigned() {
    this.router.navigate(['/departments', 'unassigned']);
  }

  onSearchChange(): void {
    if (this.searchTerm.trim()) {
      this.searching = true;
      this.employeeService.getEmployees().subscribe(employees => {
        this.searchResults = employees.filter(emp => {
          return emp.fullName.toLowerCase().includes(this.searchTerm.toLowerCase());
        });
      });
    } else {
      this.searchResults = [];
      this.searching = false;
    }
    console.log(this.searchResults);
  }

  navigateToDepartment(department: Department): void {
    if (department && department.id) {
      this.router.navigate(['/departments', department.id]);
    }
  }
  addDepartment() {
    this.showAddDepartmentModal = true;
    this.newDepartmentName = '';
  }

  closeAddDepartmentModal() {
    this.showAddDepartmentModal = false;
  }

  createDepartment() {
    if (!this.newDepartmentName.trim()) return;
    this.departmentService.createDepartment(this.newDepartmentName).subscribe(() => {
      this.loadDepartments();
      this.closeAddDepartmentModal();
    });
  }

  deleteDepartment(departmentId: string) {
      this.departmentService.deleteDepartment(departmentId).subscribe(() => {
        this.loadDepartments();
        this.loadUnassignedEmployeesCount();
      }, error => {
        console.error('Error deleting department', error);
      });
  }

  deleteUnassigned() {
      this.employeeService.deleteUnassignedEmployees().subscribe(() => {
        this.loadUnassignedEmployeesCount();
        this.loadDepartments();
      }, error => {
        console.error('Error deleting unassigned employees', error);
      });
  }
}