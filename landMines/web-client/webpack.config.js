    // webpack.config.js
    const path = require('path');
    const HtmlWebpackPlugin = require('html-webpack-plugin');

    module.exports = {
      mode: 'production', // or 'production'
      entry: './index.js', // Your main entry point
      output: {
        filename: 'bundle.js', // Output bundle file name
        path: path.resolve(__dirname, 'dist'), // Output directory
        clean: true, // Clean the dist folder before each build
      },

        module: {
        rules: [
            {
            test: /\.css$/i,
            use: ['style-loader', 'css-loader'],
            },
        ],
        },

        plugins: [
        new HtmlWebpackPlugin({
            template: './index.html', // Your HTML template
        }),
        ],
    
    };