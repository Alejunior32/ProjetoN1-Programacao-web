import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './NovoUsuario.css';

const NovoUsuario = ({ token }) => {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [createdUsuario, setCreatedUsuario] = useState(null);

  const handleCreateUsuario = async () => {
    try {
      const response = await fetch('http://localhost:8080/v1/usuarios', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email,
          senha,
          etiquetas: [], // Você pode adicionar etiquetas aqui se necessário
        }),
      });

      if (response.ok) {
        const data = await response.json();
        setCreatedUsuario(data);
      } else {
        console.error('Erro ao criar usuário:', response.status);
      }
    } catch (error) {
      console.error('Erro durante a chamada da API:', error);
    }
  };

  return (
    <div className="container mt-4 d-flex justify-content-center align-items-center flex-column">
      <div className="newQuadrado">
        <h2>Crie um novo Usuário</h2>
        <form onSubmit={(e) => {
          e.preventDefault();
          handleCreateUsuario();
        }}>
          <label htmlFor="email">Email do Usuário:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <label htmlFor="senha">Senha do Usuário:</label>
          <input
            type="password"
            id="senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
          <button type="submit">Criar Usuário</button>
        </form>
        {createdUsuario && (
          <div>
            <h3>Usuário Criado com Sucesso!</h3>
            <p>Email do Usuário: {createdUsuario.email}</p>
          </div>
        )}
        <Link to="/admin/usuarios">
          Voltar
        </Link>
      </div>
    </div>
  );
};

export default NovoUsuario;