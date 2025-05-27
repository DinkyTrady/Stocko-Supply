/** @type {import('tailwindcss').Config} **/
module.exports = {
  content: [
    "./src/main/resources/**/*.{html,js}",
    "./src/main/resources/templates/**/*.html",
    "./src/main/resources/templates/**/**/*.html",
  ], // it will be explained later
  theme: {
    extend: {
      colors: {
        primary: "#212529",
      },
    },
  },
  plugins: [],
};
