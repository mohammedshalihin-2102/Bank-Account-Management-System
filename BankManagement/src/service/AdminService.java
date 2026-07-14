package service;


import dao.AdminDao;

public class AdminService {

    AdminDao dao = new AdminDao();

    public void viewAllCustomers() {
        dao.fetchAllCustomers();
    }

    public void viewCustomerTransactions(int accountId) {
        dao.fetchTransactions(accountId);
    }

    public void deleteCustomer(int accountId) {
        dao.deleteAccount(accountId);
    }
}
