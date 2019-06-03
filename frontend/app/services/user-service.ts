import Service from '@ember/service';
import { inject } from '@ember/service';
import { AMOUNT_PAGE, fetchAjax, IPageDTO } from "./service-commons"

export enum UserRoles {
  ROLE_ADMIN = "ROLE_ADMIN",
  ROLE_BUYER = "ROLE_BUYER"
}

export interface UserDetails {
  username: string;
  name: string;
  surname: string;
  role: UserRoles;
}

export default class UserService extends Service.extend({
  makeAuthRequest: inject("make-auth-request"),
  session: inject("session" as any),
  adminNamespace: "/api/admin",
  userNamespace: "/api/user"
}) {
  login(username: string, password: string) {
      return this
      .get("session")
      .authenticate('authenticator:token', {username: username, password: password});
  }
  signin(name: string, surname: string, username: string, password: string) {
    return fetchAjax({
      type: "POST",
      url: this.get("userNamespace") + "/signin",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        name: name,
        surname: surname,
        username: username,
        password: password
      })
    });
  }
  changePassword(oldPassword: string, nextPassword: string) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("userNamespace") + "/change-password",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        password: nextPassword,
        oldPassword: oldPassword
      })
    })
    .then((data) => {
      this.get("session").invalidate();
    });
  }
  searchUser(username: string, page: number): Promise<IPageDTO<UserDetails>> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("adminNamespace") + "/search-user",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        username: username,
        page: page - 1,
        amount: AMOUNT_PAGE
      })
    }).then(page => {
      page.page += 1;
      return page;
    });
  }
  setRole(username: string, role: string) {
    console.log(role);
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("adminNamespace") + "/set-role",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        username: username,
        userRole: role
      })
    });
  }
}

// DO NOT DELETE: this is how TypeScript knows how to look up your services.
declare module '@ember/service' {
  interface Registry {
    'user-service': UserService;
  }
}
