package com.example.CRUD.enumurators;

public enum EnumUserStatus {
        ACTIVE("Active"),
        INACTIVE("Inactive");

        private final String status;

        EnumUserStatus(String status) {
                this.status = status;
        }

        public String getStatus() {
                return status;
        }
}

