<h1 style="${auth ? 'color: blue;' : 'color: red;'}">
  ${auth ? "Login Successful!" : "Login Failed!"}
</h1>
<ul style="font-family: 'monospace'; ${auth ? '' : 'display: none;'}">
  <li>Matr�cula.................: ${user.chave}</li>
  <li>Nome......................: ${user.nome}</li>
  <li>Nome Guerra...............: ${user.nomeGuerra}</li>
  <li>ChaveNome.................: ${user.chaveNome}</li>
  <li>Prefixo Depend�ncia.......: ${user.prefixoDepe}</li>
  <li>UOR Depend�ncia...........: ${user.uorDepe}</li>
  <li>UOR Equipe................: ${user.uorEquipe}</li>
</ul>