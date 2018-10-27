import Component from '@ember/component';

export default class NavbarHeader extends Component.extend({
  tagName: "nav",
  classNames: ["navbar", "is-white", "topNav"],
  isBurgerOpen: false
}) {
  actions = {
    toggleBurger(this: NavbarHeader) {
        this.toggleProperty("isBurgerOpen");
    }
}
};
