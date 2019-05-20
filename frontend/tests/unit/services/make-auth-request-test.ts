import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Service | make-auth-request', function(hooks) {
  setupTest(hooks);

  // Replace this with your real tests.
  test('it exists', function(assert) {
    let service = this.owner.lookup('service:make-auth-request');
    assert.ok(service);
  });
});

