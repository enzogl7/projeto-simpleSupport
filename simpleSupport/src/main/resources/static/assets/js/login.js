document.getElementById('telefoneCadastro').addEventListener('input', function (e) {
    let telefone = e.target.value.replace(/\D/g, '');

    if (telefone.length > 10) {
        telefone = telefone.replace(/^(\d{2})(\d{5})(\d{4}).*/, '($1) $2-$3');
    } else {
        telefone = telefone.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, '($1) $2-$3');
    }

    e.target.value = telefone;
});

document.getElementById('empresaCadastrada').addEventListener('change', function() {
    // Oculta o campo de cadastro da empresa se a empresa for cadastrada
    document.getElementById('cadastroEmpresa').style.display = 'none';
});

document.getElementById('empresaNaoCadastrada').addEventListener('change', function() {
    // Exibe o campo de cadastro da empresa se a empresa não for cadastrada
    document.getElementById('cadastroEmpresa').style.display = 'block';
});

// Mostra o campo de "empresa já cadastrada" quando o tipo de usuário for "empresa"
document.getElementById('empresa').addEventListener('change', function() {
    document.getElementById('checkEmpresaExistente').style.display = 'block';
});

// Oculta o campo de "empresa já cadastrada" quando o tipo de usuário for "usuário"
document.getElementById('usuario').addEventListener('change', function() {
    document.getElementById('checkEmpresaExistente').style.display = 'none';
    document.getElementById('cadastroEmpresa').style.display = 'none'; // Esconde também se estiver visível
});

document.getElementById('cnpjEmpresa').addEventListener('input', function() {
    var cnpj = this.value;
    this.value = formatCNPJ(cnpj);
});

function modalCadastro() {
    $('#modalCadastro').modal('show');
}

function validarSenha(senha) {
    var regexSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regexSenha.test(senha);
}

function verificarCamposEmpresa() {
    var nomeEmpresa = document.getElementById('nomeEmpresa').value;
    var cnpjEmpresa = document.getElementById('cnpjEmpresa').value;
    var emailEmpresa = document.getElementById('emailEmpresa').value;
    var razaoSocialEmpresa = document.getElementById('razaoSocialEmpresa').value;
    var emailResponsavelEmpresa = document.getElementById('emailResponsavelEmpresa').value;

    if (document.getElementById('cadastroEmpresa').style.display !== 'none') {

        if (!nomeEmpresa || !cnpjEmpresa || !emailEmpresa || !razaoSocialEmpresa || !emailResponsavelEmpresa) {
            Swal.fire({
                title: "Ops!",
                text: "Preencha todos os campos de empresa!",
                icon: "warning",
                confirmButtonText: 'OK'
            });
            return false;
        }
    }

    return true;
}

function cadastrar() {
    var nomeCadastro = document.getElementById('nomeCadastro').value;
    var sobrenomeCadastro = document.getElementById('sobrenomeCadastro').value;
    var emailCadastro = document.getElementById('emailCadastro').value;
    var telefoneCadastro = document.getElementById('telefoneCadastro').value;
    var senhaCadastro = document.getElementById('senhaCadastro').value;
    var tipoUsuario;
    if (document.getElementById('usuario').checked) {
        tipoUsuario = 'usuario';
    } else if (document.getElementById('empresa').checked) {
        tipoUsuario = 'empresa';
    }
    var nomeEmpresa = document.getElementById('nomeEmpresa').value;
    var cnpjEmpresa = document.getElementById('cnpjEmpresa').value;
    var emailEmpresa = document.getElementById('emailEmpresa').value;
    var razaoSocialEmpresa = document.getElementById('razaoSocialEmpresa').value;
    var emailResponsavelEmpresa = document.getElementById('emailResponsavelEmpresa').value;

    if (!nomeCadastro || !sobrenomeCadastro || !emailCadastro || !telefoneCadastro || !senhaCadastro || !tipoUsuario) {
        Swal.fire({
            title: "Ops!",
            text: "Preencha todos os campos!",
            icon: "warning",
            confirmButtonText: 'OK'
        })
        return;
    }

    if(!validarSenha(senhaCadastro)) {
        exibirMensagemErro(mensagemErroSenha, 'A senha deve ter no mínimo 8 caracteres, incluindo maiúsculas, minúsculas, números e caracteres especiais.');
        return;
    }

    if(!validarCNPJ(cnpjEmpresa) && document.getElementById('cadastroEmpresa').style.display !== 'none') {
        exibirMensagemErro(mensagemErroCnpj, 'O CNPJ está em um formato inválido ou vazio.');
        return;
    }

    if(!verificarCamposEmpresa()) {
        return;
    }

    $.ajax({
        url: '/cadastrar',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            nome: nomeCadastro,
            sobrenome: sobrenomeCadastro,
            email: emailCadastro,
            telefone: telefoneCadastro,
            senha: senhaCadastro,
            role: "USER",
            tipoUsuario: tipoUsuario,
            nomeEmpresa: nomeEmpresa,
            cnpjEmpresa: cnpjEmpresa,
            emailEmpresa: emailEmpresa,
            razaoSocialEmpresa: razaoSocialEmpresa,
            emailResponsavelEmpresa: emailResponsavelEmpresa
        }),
        complete: function(xhr, status) {
            switch (xhr.status) {
                case 200:
                    $('#modalCadastro').modal('hide')
                    Swal.fire({
                        title: "Pronto!",
                        text: "Cadastrado com sucesso!",
                        icon: "success",
                        confirmButtonText: 'OK'
                    })
                    break;
                case 406:
                    Swal.fire({
                        title: "Ops!",
                        text: "Email já cadastrado.",
                        icon: "warning",
                        confirmButtonText: 'OK'
                    })
                    break;
                case 409:
                    Swal.fire({
                        title: "Ops!",
                        text: "Telefone já cadastrado.",
                        icon: "warning",
                        confirmButtonText: "OK"
                    })
                    break;
                case 404:
                    Swal.fire({
                        title: "Ops!",
                        text: "Não existe um usuário com esse email informado para ser o responsável pela empresa.",
                        icon: "warning",
                        confirmButtonText: "OK"
                    })
                    break;
                case 500:
                    $('#modalCadastro').modal('hide')
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao cadastrar-se.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}

function modalLogin() {
    $('#modalLogin').modal('show');
}

function logar() {
    var emailLogin = document.getElementById('emailLogin').value;
    var senhaLogin = document.getElementById('senhaLogin').value;

    $.ajax({
        url: '/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            email: emailLogin,
            password: senhaLogin
        }),
        success: function(response) {
            window.location.href = "/dashboard/home";
        },
        error: function(xhr, status, error) {
            Swal.fire({
                title: "Ops!",
                text: "Email ou senha inválidos.",
                icon: "error",
                confirmButtonText: "OK"
            });
        }
    });
}
