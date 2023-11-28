import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './NovoAdmin.css';

const NovoAdmin = ({ token }) => {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [createdAdmin, setCreatedAdmin] = useState(null);

  const handleCreateAdmin = async () => {
    try {
      const response = await fetch('http://localhost:8080/v1/admins', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email,
          senha,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        setCreatedAdmin(data);
      } else {
        console.error('Erro ao criar admin:', response.status);
      }
    } catch (error) {
      console.error('Erro durante a chamada da API:', error);
    }
  };

  return (
    <div className="container mt-4 d-flex justify-content-center align-items-center flex-column">
      <div className="newQuadrado">
        <h2>Crie um novo Admin</h2>
        <form onSubmit={(e) => {
          e.preventDefault();
          handleCreateAdmin();
        }}>
          <label htmlFor="email">Email do Admin:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <label htmlFor="senha">Senha do Admin:</label>
          <input
            type="password"
            id="senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
          <button type="submit">Criar Admin</button>
        </form>
        {createdAdmin && (
          <div>
            <h3>Admin Criado com Sucesso!</h3>
            <p>Email do Admin: {createdAdmin.email}</p>
          </div>
        )}
        <Link to="/admin/admins">
          Voltar
        </Link>
      </div>
    </div>
  );
};

export default NovoAdmin;