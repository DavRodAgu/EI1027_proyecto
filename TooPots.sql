-- tablas
CREATE TABLE Acredita (
    idTipoActividad int  NOT NULL,
    idAcreditacion int  NOT NULL,
    CONSTRAINT Acredita_pk PRIMARY KEY (idTipoActividad,idAcreditacion)
);

CREATE TABLE Acreditacion (
    idAcreditacion serial  NOT NULL,
    certificado varchar(100)  NOT NULL,
    estado varchar(15)  NOT NULL,
    idInstructor varchar(10)  NOT NULL,
    CONSTRAINT estado CHECK (estado IN ('aceptada', 'rechazada')) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT Acreditacion_pk PRIMARY KEY (idAcreditacion)
);

CREATE TABLE Actividad (
    idActividad serial  NOT NULL,
    estado varchar(15)  NOT NULL,
    nombre varchar(50)  NOT NULL,
    descripcion varchar(200)  NOT NULL,
    duracion time  NOT NULL,
    fecha date  NOT NULL,
    precio decimal(10,2)  NOT NULL,
    minAsistentes int  NOT NULL,
    maxAsistentes int  NOT NULL,
    lugar varchar(50)  NOT NULL,
    puntoDeEncuentro varchar(50)  NOT NULL,
    horaDeEncuentro time  NOT NULL,
    textoCliente varchar(50)  NOT NULL,
    idTipoActividad int  NOT NULL,
    idInstructor varchar(10)  NOT NULL,
    CONSTRAINT estadoActividad CHECK (estado IN ('abierta','cerrada','completa','cancelada')) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT precio CHECK (precio >= 0) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT minAsistentes CHECK (minAsistentes >= 0) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT maxAsistentes CHECK (maxAsistentes >= 0) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT minMenorMaxAsistentes CHECK (minAsistentes <= maxAsistentes) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT Actividad_pk PRIMARY KEY (idActividad)
);

CREATE TABLE Cliente (
    idCliente varchar(10)  NOT NULL,
    nombre varchar(50)  NOT NULL,
    email varchar(50)  NOT NULL,
    sexo char(1)  NOT NULL,
    fechaNacimiento date  NOT NULL,
    CONSTRAINT sexo CHECK (sexo IN ('M', 'F')) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT Cliente_pk PRIMARY KEY (idCliente)
);

CREATE TABLE Comentario (
    idComentario serial  NOT NULL,
    comentario varchar(50)  NOT NULL,
    valoracion int  NOT NULL,
    idCliente varchar(10)  NOT NULL,
    idActividad int  NULL,
    idInstructor varchar(10)  NULL,
    CONSTRAINT valoracion CHECK (valoracion >= 1 and valoracion <= 5) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT Comentario_pk PRIMARY KEY (idComentario)
);

CREATE TABLE ImagenPromocional (
    idImagen serial  NOT NULL,
    imagen varchar(100)  NOT NULL,
    idActividad int  NOT NULL,
    CONSTRAINT ImagenPromocional_pk PRIMARY KEY (idImagen)
);

CREATE TABLE Instructor (
    idInstructor varchar(10)  NOT NULL,
    estado varchar(15)  NOT NULL DEFAULT 'pendiente',
    nombre varchar(50)  NOT NULL,
    email varchar(50)  NOT NULL,
    iban varchar(20)  NOT NULL,
    foto varchar(100)  NOT NULL,
    CONSTRAINT estadoInstructor CHECK (estado IN ('aceptada','rechazada', 'pendiente')) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT Instructor_pk PRIMARY KEY (idInstructor)
);

CREATE TABLE Prefiere (
    idTipoActividad int  NOT NULL,
    idCliente varchar(10)  NOT NULL,
    CONSTRAINT Prefiere_pk PRIMARY KEY (idTipoActividad,idCliente)
);

CREATE TABLE Reserva (
    idReserva serial  NOT NULL,
    estadoPago varchar(15)  NOT NULL DEFAULT 'pendiente',
    numTransaccion int  NOT NULL,
    fecha date  NOT NULL,
    numAsistentes int  NOT NULL,
    precioPorPersona decimal(10,2)  NOT NULL,
    idActividad int  NOT NULL,
    idCliente varchar(10)  NOT NULL,
    CONSTRAINT estadoPago CHECK (estadoPago IN ('pendiente','pagado')) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT precio CHECK (precioPorPersona >= 0) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT transaccion CHECK (numTransaccion >= 0) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT asistentes CHECK (numAsistentes >= 0) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT Reserva_pk PRIMARY KEY (idReserva)
);

CREATE TABLE TipoActividad (
    idTipoActividad serial  NOT NULL,
    nombre varchar(50)  NOT NULL,
    nivel varchar(15)  NOT NULL,
    CONSTRAINT nivel CHECK (nivel IN ('bajo', 'medio', 'alto', 'extremo')) NOT DEFERRABLE INITIALLY IMMEDIATE,
    CONSTRAINT TipoActividad_pk PRIMARY KEY (idTipoActividad)
);

-- claves ajenas
ALTER TABLE Acredita ADD CONSTRAINT Acredita_Acreditacion
    FOREIGN KEY (idAcreditacion)
    REFERENCES Acreditacion (idAcreditacion)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Acredita ADD CONSTRAINT Acredita_TipoActividad
    FOREIGN KEY (idTipoActividad)
    REFERENCES TipoActividad (idTipoActividad)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Acreditacion ADD CONSTRAINT Acreditacion_Instructor
    FOREIGN KEY (idInstructor)
    REFERENCES Instructor (idInstructor)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Actividad ADD CONSTRAINT Actividad_Instructor
    FOREIGN KEY (idInstructor)
    REFERENCES Instructor (idInstructor)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Actividad ADD CONSTRAINT Actividad_TipoActividad
    FOREIGN KEY (idTipoActividad)
    REFERENCES TipoActividad (idTipoActividad)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Comentario ADD CONSTRAINT Comentario_Actividad
    FOREIGN KEY (idActividad)
    REFERENCES Actividad (idActividad)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Comentario ADD CONSTRAINT Comentario_Cliente
    FOREIGN KEY (idCliente)
    REFERENCES Cliente (idCliente)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Comentario ADD CONSTRAINT Comentario_Instructor
    FOREIGN KEY (idInstructor)
    REFERENCES Instructor (idInstructor)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE ImagenPromocional ADD CONSTRAINT ImagenPromocional_Actividad
    FOREIGN KEY (idActividad)
    REFERENCES Actividad (idActividad)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Prefiere ADD CONSTRAINT Prefiere_Cliente
    FOREIGN KEY (idCliente)
    REFERENCES Cliente (idCliente)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Prefiere ADD CONSTRAINT Prefiere_TipoActividad
    FOREIGN KEY (idTipoActividad)
    REFERENCES TipoActividad (idTipoActividad)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Reserva ADD CONSTRAINT Reserva_Actividad
    FOREIGN KEY (idActividad)
    REFERENCES Actividad (idActividad)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Reserva ADD CONSTRAINT Reserva_Cliente
    FOREIGN KEY (idCliente)
    REFERENCES Cliente (idCliente)
    ON DELETE  RESTRICT 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

