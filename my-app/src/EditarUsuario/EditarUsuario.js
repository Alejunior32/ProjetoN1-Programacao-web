import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';
import './EditarUsuario.css';

const EditarUsuario = ({ token }) => {
  const {idUsuario} = useParams();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [editadoComSucesso, setEditadoComSucesso] = useState(false);

  useEffect(() => {
    const fetchUsuario = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/v1/usuarios/${idUsuario}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        const usuario = response.data;
        setEmail(usuario.email);
      } catch (error) {
        console.error('Erro ao buscar usuário:', error);
      }
    };

    fetchUsuario();
  }, [idUsuario, token]);

  const handleEditarUsuario = async () => {
    try {
      await axios.put(
        `http://localhost:8080/v1/usuarios/${idUsuario}`,
        { email, senha },
        {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setEditadoComSucesso(true);
    } catch (error) {
      console.error('Erro ao editar usuário:', error);
    }
  };

  return (
    <div className="container">
      {editadoComSucesso ? (
        <div className='newQuadrado'>
          <h2>Usuário editado com sucesso!</h2>
          <Link to="/admin/usuarios" className="link">
            Voltar para a lista de usuários
          </Link>
        </div>
      ) : (
        <div className="form-wrapper">
          <h2>Editar Usuário</h2>
          <label htmlFor="email">Novo Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <label htmlFor="senha">Nova Senha:</label>
          <input
            type="password"
            id="senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
          <div className="button-container">
            <button className='btn btn-warning' onClick={handleEditarUsuario}>
              Salvar Edições
            </button>
          </div>
          <Link to="/admin/usuarios" className="link">
            Cancelar
          </Link>
        </div>
      )}
    </div>
  );
  }

export default EditarUsuario;