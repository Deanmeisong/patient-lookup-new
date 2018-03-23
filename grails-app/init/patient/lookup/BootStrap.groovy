package patient.lookup

class BootStrap {

    def init = { servletContext ->
        System.out.println("init out")

        initSecurityRoles()
        initAdmin()
    }
    def destroy = {
    }

    def initAdmin() {
        createUser('u4944587', 'philip.wu@anu.edu.au', 'fakePassword', RoleType.ADMIN)
        createUser('u5454816', 'u5454816@anu.edu.au', 'Bluesky85', RoleType.ADMIN)
    }

    def initSecurityRoles() {
        System.out.println("initSecurityRoles")
        // Initialize each user role
        for (RoleType rt : RoleType.values()) {
            System.out.println("Saving role: "+rt)
            Role r = new Role(authority: rt.toString())
            boolean saved =  r.save(flush: true)
        }
    }

    private void createUser(String username,String email, String password, RoleType roleType){
        Role role = Role.findByAuthority(roleType.toString())
        User user = User.findByUsername(username)
        if (user == null) {
            user = new User(username: username, email: email, displayName: username, enabled: true, password: password)
            boolean saved = user.save(flush: true)
            System.out.println("Saved? "+saved+" Errors saving user [" + username + "]: "+user.errors)
        }

        UserRole userRole = UserRole.findByUserAndRole(user, role)
        if (userRole == null) {
            userRole = new UserRole(user: user, role: role)
            boolean saved = userRole.save(flush: true)
            System.out.println("Saved? "+saved+" Errors saving user role [user:" + username + ", role:" + roleType.toString() + "]: "+userRole.errors)
        }
    }


}
