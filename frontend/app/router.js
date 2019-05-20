import EmberRouter from '@ember/routing/router';
import config from './config/environment';

const Router = EmberRouter.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route("login");
  this.route("register");
  this.route('profile', function() {});
  this.route('fuel-catalogue', function() {
    this.route('buy', { path: "/buy/:fuel_id" });
  });
  this.route('about');
  this.route('user', function() {
    this.route('manage-fuel');
    this.route('change-password');
    this.route('search-user-operations');
    this.route('manage-tariff');
    this.route('statistics');
    this.route('my-operations', function() {
      this.route('index');
      this.route('page', { path: "/page/:page_number" });
    });
    this.route('manage-authorities', function() {});
    this.route('search-operations');
  });
});

export default Router;
