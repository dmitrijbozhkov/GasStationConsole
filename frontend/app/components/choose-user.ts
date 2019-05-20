import Component from '@ember/component';
import { inject } from '@ember/service';
import { observer } from '@ember/object';
import { amountPages } from "./common-functions";
import { UserDetails } from "../services/user-service";

export default class ChooseUser extends Component.extend({
  positionalParams: ["isLoading", "chosenUser"],
  notify: inject("toast" as any) as any,
  userService: inject("user-service"),
  searchEmail: "",
  foundUsers: [],
  currentPage: 0,
  amountPages: 0,
  pagerChange: false,
  pageChange: observer("currentPage", function (this: ChooseUser) {
    if (this.get("pagerChange")) {
      this.searchPage(this.get("currentPage"));
    }
  })
}) {
  searchPage(page: number) {
    this.set("pagerChange", false)
    this.set("isLoading" as any, true);
    this.get("userService")
    .searchUser(this.get("searchEmail"), page)
    .then((found) => {
      this.set("foundUsers", found.content);
      this.set("currentPage", found.page);
      this.set("amountPages", amountPages(found.total, found.amount));
      this.set("isLoading" as any, false);
    })
    .catch((err) => {
      this.set("isLoading" as any, false);
      this.notify.error(err.responseJSON.exceptionMessage, "Search error");
    })
    .finally(() => {
      this.set("pagerChange", true);
    });
  }
  actions = {
    searchUser(this: ChooseUser) {
      if (this.get("searchEmail")) {
        this.searchPage(1);
      }
    },
    chooseUser(this: ChooseUser, user: UserDetails) {
      this.set("chosenUser" as any, user);
      this.set("foundUsers", []);
      this.set("searchEmail", "")
    },
    dismissUser(this: ChooseUser) {
      this.set("chosenUser" as any, null);
    }
  }
};
