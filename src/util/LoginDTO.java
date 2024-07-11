package util;

import java.io.Serializable;

public class LoginDTO implements Serializable {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setClientType(String Client_type) {
        this.Client_type = Client_type;
    }

    public String getClientType() {
        return Client_type;
    }

    private String userName;
    private String password;
    private boolean status;
    private String Client_type;
}
