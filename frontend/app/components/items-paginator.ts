import Component from '@ember/component';
import { computed } from '@ember/object';

export default class ItemsPaginator extends Component.extend({
  positionalParams: ["currentPage", "amountPages"],
  isNextPageNotExists: computed("currentPage", "amountPages", function() {
    return !(this.get("currentPage" as any) < this.get("amountPages"));
  }),
  isPreviousPageNotExists: computed("currentPage", "amountPages", function() {
    return !(this.get("currentPage" as any) > 1);
  }),
  isMorePagesForward: computed("currentPage", "amountPages", function() {
    return (this.get("currentPage") + 2) < this.get("amountPages");
  }),
  isMorePagesBack: computed("currentPage", "amountPages", function() {
    return this.get("currentPage") > 3;
  }),
  pageView: computed("currentPage", "amountPages", function() {
    let page = this.get("currentPage");
    let view = [{pageNumber: page, isCurrent: true}];
    if (!this.get("isPreviousPageNotExists")) {
      view.unshift({pageNumber: page - 1, isCurrent: false});
    }
    if (!this.get("isNextPageNotExists")) {
      view.push({pageNumber: page + 1, isCurrent: false});
    }
    return view;
  })
}) {
  actions = {
    nextPage(this: ItemsPaginator) {
      if (this.get("currentPage" as any) >= this.get("amountPages" as any)) {
        return;
      }
      this.set("currentPage" as any, this.get("currentPage" as any) + 1);
    },
    previousPage(this: ItemsPaginator) {
      if (this.get("currentPage" as any) === 1) {
        return;
      }
      this.set("currentPage" as any, this.get("currentPage" as any) - 1);
    },
    movePage(this: ItemsPaginator, pageNumber: number) {
      this.set("currentPage" as any, pageNumber);
    }
  }
};