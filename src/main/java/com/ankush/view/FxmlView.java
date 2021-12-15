package com.ankush.view;

import java.util.ResourceBundle;

public enum FxmlView {

    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/home/Login.fxml";
        }
    },
    ADDUSER {
        @Override
        public String getTitle() {

            //return getStringFromResourceBundle("login.title");
            return "Add New User";
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/create/CreateUser.fxml";
        }
    },
    NOTIFICATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/ui/Notification.fxml";
        }
    },
    HOME {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/home/Home.fxml";
        }
    },
    CUSTOMER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/create/AddCustomer.fxml";
        }
    },
    BANK {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/create/AddBank.fxml";
        }
    },
    BILLING {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/BillingFrame.fxml";
        }
    },
    RATE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/SetRate.fxml";
        }
    },
    DASHBOARD {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/dashboard/Dashboard.fxml";
        }
    };
    public abstract String getTitle();
    public abstract String getFxmlFile();
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
