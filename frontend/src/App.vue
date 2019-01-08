<template>
  <div id="app">
    <nav class="grid-container">
      <router-link to="/rentalOffice/warehouse"><div class="nav-content">Database</div></router-link>
      <router-link to="/"><div class="nav-content">Rent</div></router-link>
      <router-link to="/"><div class="nav-content">Add</div></router-link>
    </nav>
    <main>
      <router-view/>
    </main>
  </div>
</template>

<style>
  /*! minireset.css v0.0.3 | MIT License | github.com/jgthms/minireset.css */html,body,p,ol,ul,li,dl,dt,dd,blockquote,figure,fieldset,legend,textarea,pre,iframe,hr,h1,h2,h3,h4,h5,h6{margin:0;padding:0}h1,h2,h3,h4,h5,h6{font-size:100%;font-weight:normal}ul{list-style:none}button,input,select,textarea{margin:0}html{box-sizing:border-box}*,*:before,*:after{box-sizing:inherit}img,embed,iframe,object,audio,video{height:auto;max-width:100%}iframe{border:0}table{border-collapse:collapse;border-spacing:0}td,th{padding:0;text-align:left}a{text-decoration:none;color:inherit;}

  /*MAIN STYLE*/
  :root {
    --nav-margin: 7%;
  }
  html {
    font-family: Verdana, Helvetica, Arial;
    background-color: #121D2D;
    color: #fff;
  }
  main {
    margin-left: var(--nav-margin);
    padding: 2% 25px 2% 25px;
  }
  .grid-container {
    display: grid;
  }
  h1 {
    font-size: 50px;
  }

  .v-table {
    color: #000;
    background-color: #FFF;
    margin-top: 8px;
  }
  button {
    margin: 0 2px;
  }
  .modal-box {
    width: 80%;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: #000;
    font-size: 14px;
  }
  .modal-box .modal-box-title {
    display: block;
    font-size: 22px;
    border-bottom: 1px solid #CCC;
    margin-bottom: 10px;
  }
  .modal-box input, .modal-box textarea {
    width: 95%;
    margin: 6px;
    padding: 4px;
  }
  .modal-box button {
    padding: 4px 40px;
  }
  .alert-modal-box {
    text-align: center;
  }
  .alert-modal-box button{
    display: block;
    margin: auto;
    margin-top: 20px;
    padding: 4px 40px;
  }
/*NAVIGATION*/
  nav {
    height: 100%;
    width: var(--nav-margin);
    overflow: auto;
    position: fixed;
    background-color: #242931;
  }
  nav > a {
    overflow: hidden;
    display: grid;
    width: 100%;
    height: 100%;
    border: 1px solid #555;
    transition: background-color 0.3s;
  }
  nav > a:hover {
    background-color: #2B323C;
  }
  .nav-content {
    margin: 10%;
    overflow: hidden;
    color: #CCC;
    line-height: 1.5;
    font-size: 1.15vw;
    text-shadow: 0 0 0px #FFF;
    text-align: center;
    justify-self: center;
    align-self: center;
    transition: letter-spacing 0.3s, color 0.5s, text-shadow 0.4s;
  }
  nav > a:hover .nav-content {
    text-shadow: 0 0 3px #EEE;
    letter-spacing: 0.15vw;
    color: #FFF;
  }
</style>
