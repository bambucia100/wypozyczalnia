// vue.config.js
module.exports = {
  devServer: {
    proxy: 'http://localhost:8082'
  },
  outputDir: 'target/dist',
  assetsDir: 'static'
}
