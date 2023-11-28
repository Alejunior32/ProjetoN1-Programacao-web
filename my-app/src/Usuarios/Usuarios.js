import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Usuarios.css';

const Usuarios = ({ token }) => {
  const [usuarios, setUsuarios] = useState([]);
  const [usuarioExcluido, setUsuarioExcluido] = useState(null);

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        const response = await fetch('http://localhost:8080/v1/usuarios', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (response.ok) {
          const data = await response.json();
          setUsuarios(data.content);
        } else {
          console.error('Erro ao buscar usuários:', response.status);
        }
      } catch (error) {
        console.error('Erro durante a chamada da API:', error);
      }
    };

    fetchUsuarios();
  }, [token, usuarioExcluido]);

  const handleExcluirUsuario = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8080/v1/usuarios/${userId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.ok) {
        setUsuarioExcluido(userId);
      } else {
        console.error('Erro ao excluir usuário:', response.status);
      }
    } catch (error) {
      console.error('Erro durante a chamada da API:', error);
    }
  };

  return (
    <div className="etiquetas-container">
      <div className="quadrado">
        <h2>Lista de Usuários</h2>
        <div className="etiquetas-header">
          <Link to="/admin/usuarios/novoUsuario" className="btn btn-primary">Criar Novo Usuário</Link>
        </div>
        <table className="etiquetas-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Email</th>
              <th>Detalhes</th>
              <th>Editar</th>
              <th>Deletar</th>
            </tr>
          </thead>
          <tbody>
            {usuarios.map((user) => (
              <tr key={user.id} className="etiqueta-item">
                <td>{user.id}</td>
                <td>{user.email}</td>
                <td><Link to={`/admin/usuarios/detalhes/${user.id}`} className='btn btn-primary'>Detalhes</Link></td>
                <td><Link to={`/admin/usuarios/editar/${user.id}`} className='btn btn-warning'>Editar</Link></td>
                <td>
                  <button
                    className='btn btn-danger'
                    onClick={() => handleExcluirUsuario(user.id)}>
                    Excluir
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <Link to={`/admin/admins`} className='btn btn-dark'>Lista Admins</Link>
      </div>
    </div>
  );
};

export default Usuarios;