import { Department } from "./department.model";

export interface Employee {
  id: string;
  fullName: string;
  address: string;
  phoneNumber: string;
  email: string;
  department: Department | null; // null if the employee is unassigned
}