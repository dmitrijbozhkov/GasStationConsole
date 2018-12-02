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
    this.route('my-operations');
    this.route('change-password');
    this.route('manage-authorities');
    this.route('search-user-operations');
  });
});

export default Router;
