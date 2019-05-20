import Component from '@ember/component';
import { inject } from '@ember/service';
import { observer } from '@ember/object';
import { UserRoles } from "../services/user-service";

export default class UserAuthorities extends Component.extend({
  userService: inject("user-service"),
  notify: inject("toast" as any) as any,
  isLoading: false,
  chosenUser: null,
  roleRepr: [
    { role: UserRoles.ROLE_ADMIN, repr: "Admin", isCurrent: false },
    { role: UserRoles.ROLE_BUYER, repr: "Buyer", isCurrent: false }
  ],
  selectedRole: null,
  userChoose: observer("chosenUser", function (this: UserAuthorities) {
    if (this.get("chosenUser")) {
      let foundRepr;
      let roleRepr = this.get("roleRepr");
      for (let i = 0; i < roleRepr.length; i += 1) {
        if (roleRepr[i].role === this.get("chosenUser" as any).role) {
          foundRepr = roleRepr[i];
        }
        roleRepr[i].isCurrent = false;
      }
      if (foundRepr) {
        foundRepr.isCurrent = true;
      }
    }
  })
}) {
  actions = {
    roleSelected(this: UserAuthorities) {
      this.set("selectedRole", (event as any).target.value);
    },
    changeRole(this: UserAuthorities) {
      this.set("isLoading", true);
      this.get("userService")
        .setRole(this.get("chosenUser" as any).username, this.get("selectedRole" as any))
        .then(result => {
          this.set("isLoading", false)
          this.get("notify").success("User role was changed", "Role changing successfull");
          this.set("chosenUser", null);
          this.set("selectedRole", null);
        })
        .catch(error => {
          this.set("isLoading", false);
          this.notify.error(error.responseJSON, "Error changing role");
        });
    }
  }
};
