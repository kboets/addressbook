var defaultTarget = 'http://localhost:8080';
  module.exports = [
    {
      context: ['/addressbook/v1/**'],
target: defaultTarget,
  changeOrigin: true,
}
];
