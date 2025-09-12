# Algorithm Explorer

## 项目描述 (Project Description)

This repository contains a collection of algorithms and utilities implemented in Java. The project is designed to provide solutions for various algorithmic problems, including string matching, maximum subarray problems, and N-Queens problems. It also includes a graphical user interface (GUI) for interacting with the algorithms and a database layer for managing problem data.

本项目来源于中国科学技术大学的程序设计进阶课程大作业，课程号CS400901，本项目得分较高，欢迎参考，如有帮助请点个star，谢谢！该项目包含一系列用Java实现的算法和工具。项目旨在为各种算法问题提供解决方案，包括字符串匹配、最大子数组问题和N皇后问题。项目还包括一个用于与算法交互的图形用户界面（GUI）以及一个用于管理问题数据的数据库层。

---

## 功能 (Features)

- **算法实现 (Algorithm Implementations)**: 提供多种算法的实现，包括：
  - 字符串匹配算法（如KMP、Boyer-Moore）
  - 最大子数组问题（动态规划、枚举）
  - N皇后问题（回溯法、最小冲突法）
- **图形用户界面 (GUI)**: 提供用户友好的界面，用于运行和测试算法。
- **数据库支持 (Database Support)**: 管理算法代码、问题信息和测试用例。
- **模块化设计 (Modular Design)**: 包含多个模块，如服务层、数据访问层、策略模式等。

---

## 目录结构 (Directory Structure)

```
experiment-codes/
├── files/                # 输入文件
├── lib/                  # 配置文件
├── src/                  # 源代码
│   ├── cn/edu/ustc/algorithm/  # 算法实现
│   ├── cn/edu/ustc/app/        # 应用入口
│   ├── cn/edu/ustc/dao/        # 数据访问层
│   ├── cn/edu/ustc/exception/  # 异常处理
│   ├── cn/edu/ustc/gui/        # 图形用户界面
│   ├── cn/edu/ustc/model/      # 数据模型
│   ├── cn/edu/ustc/service/    # 服务层
│   └── cn/edu/ustc/util/       # 工具类
├── uml/                  # UML图
└── README.md             # 项目说明文件
```

---

## 使用说明 (Usage)

1. **运行项目 (Run the Project)**:
   - 使用IDE（如IntelliJ IDEA）打开项目。
   - 配置运行环境并运行`AlgorithmApp`类。

2. **测试算法 (Test Algorithms)**:
   - 在GUI中选择算法类型。
   - 输入测试数据并运行。

3. **扩展功能 (Extend Functionality)**:
   - 在`strategy`包中添加新的算法策略。
   - 在`gui`包中更新界面。

---

## 开发者信息 (Author Information)

- **作者 (Author)**: SiriusPaul
- **版本 (Version)**: 1.0
- **日期 (Date)**: 2025/09/11

---

## 许可证 (License)

This project is licensed under the MIT License. See the LICENSE file for details.

此项目根据MIT许可证授权。有关详细信息，请参阅LICENSE文件。

---

## 数据库结构 (Database Schema)

The database schema for this project is designed to store algorithm codes, problem information, and test cases. Below is the structure of the main tables:

### 1. `algorithm_codes`
- **Description**: Stores the source code for different algorithms.
- **Columns**:
  - `id` (INT, PRIMARY KEY, AUTO_INCREMENT): Unique identifier for the algorithm.
  - `name` (VARCHAR(255)): Name of the algorithm.
  - `code` (TEXT): Source code of the algorithm.
  - `created_at` (DATETIME): Timestamp of when the record was created.

### 2. `problems`
- **Description**: Stores information about different problems.
- **Columns**:
  - `id` (INT, PRIMARY KEY, AUTO_INCREMENT): Unique identifier for the problem.
  - `type` (VARCHAR(255)): Type of the problem (e.g., String Matching, Max Subarray).
  - `description` (TEXT): Description of the problem.
  - `created_at` (DATETIME): Timestamp of when the record was created.

### 3. `test_cases`
- **Description**: Stores test cases for the problems.
- **Columns**:
  - `id` (INT, PRIMARY KEY, AUTO_INCREMENT): Unique identifier for the test case.
  - `problem_id` (INT, FOREIGN KEY): References the `id` column in the `problems` table.
  - `input` (TEXT): Input data for the test case.
  - `expected_output` (TEXT): Expected output for the test case.
  - `created_at` (DATETIME): Timestamp of when the record was created.

### Relationships
- The `test_cases` table has a foreign key relationship with the `problems` table, linking test cases to their respective problems.

### Example SQL Script
Below is an example SQL script to create the database schema:

```sql
CREATE TABLE algorithm_codes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE problems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE test_cases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    problem_id INT NOT NULL,
    input TEXT NOT NULL,
    expected_output TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (problem_id) REFERENCES problems(id)
);
```

此项目的数据库模式设计用于存储算法代码、问题信息和测试用例。以下是主要表的结构：

### 1. `algorithm_codes`
- **描述**: 存储不同算法的源代码。
- **字段**:
  - `id` (INT, PRIMARY KEY, AUTO_INCREMENT): 算法的唯一标识符。
  - `name` (VARCHAR(255)): 算法名称。
  - `code` (TEXT): 算法的源代码。
  - `created_at` (DATETIME): 记录创建的时间戳。

### 2. `problems`
- **描述**: 存储不同问题的信息。
- **字段**:
  - `id` (INT, PRIMARY KEY, AUTO_INCREMENT): 问题的唯一标识符。
  - `type` (VARCHAR(255)): 问题类型（例如，字符串匹配、最大子数组）。
  - `description` (TEXT): 问题的描述。
  - `created_at` (DATETIME): 记录创建的时间戳。

### 3. `test_cases`
- **描述**: 存储问题的测试用例。
- **字段**:
  - `id` (INT, PRIMARY KEY, AUTO_INCREMENT): 测试用例的唯一标识符。
  - `problem_id` (INT, FOREIGN KEY): 引用 `problems` 表中的 `id` 字段。
  - `input` (TEXT): 测试用例的输入数据。
  - `expected_output` (TEXT): 测试用例的预期输出。
  - `created_at` (DATETIME): 记录创建的时间戳。

### 关系
- `test_cases` 表与 `problems` 表通过外键建立关系，将测试用例与其对应的问题关联起来。

### 示例 SQL 脚本
以下是创建数据库模式的示例 SQL 脚本：

```sql
CREATE TABLE algorithm_codes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE problems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE test_cases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    problem_id INT NOT NULL,
    input TEXT NOT NULL,
    expected_output TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (problem_id) REFERENCES problems(id)
);
```
